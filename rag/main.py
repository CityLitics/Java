import os
from fastapi import FastAPI
from langchain_community.document_loaders import PyPDFLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_ollama import OllamaEmbeddings, ChatOllama
from langchain_community.vectorstores import Chroma
from langchain.chains.combine_documents import create_stuff_documents_chain
from langchain.chains import create_retrieval_chain
from langchain_core.prompts import ChatPromptTemplate

app = FastAPI()

NOME_ARQUIVO = "documento.pdf"
qa_chain = None  # Variável global para ser usada no endpoint /ask

print(">>> Iniciando RAG Local com Ollama <<<")

if not os.path.exists(NOME_ARQUIVO):
    print(f"AVISO: {NOME_ARQUIVO} não encontrado.")
    chunks = []
else:
    print(f"Lendo o arquivo: {NOME_ARQUIVO}")
    loader = PyPDFLoader(NOME_ARQUIVO)
    data = loader.load()
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=300, chunk_overlap=50)
    chunks = text_splitter.split_documents(data)

# >>> BANCO DE DADOS <<<
try:
    if chunks:
        print("--- Criando banco vetorial (ChromaDB) ---")
        embeddings = OllamaEmbeddings(model="mxbai-embed-large")
        llm = ChatOllama(model="llama3.2:1b")
        
        vector_db = Chroma.from_documents(
            documents=chunks,
            embedding=embeddings,
            persist_directory="./db_local"
        )
        
        prompt = ChatPromptTemplate.from_template("""
        Responda à pergunta baseando-se apenas no contexto fornecido:
        <context>
        {context}
        </context>
        Pergunta: {input}""")

        combine_docs_chain = create_stuff_documents_chain(llm, prompt)
        qa_chain = create_retrieval_chain(vector_db.as_retriever(), combine_docs_chain)
        
        print("--- TUDO PRONTO: Sistema de busca ativo! ---")
except Exception as e:
    print(f"ERRO CRÍTICO NA INICIALIZAÇÃO: {str(e)}")

# >>> JAVA <<<

@app.get("/")
def home():
    return {"status": "Online", "documento": NOME_ARQUIVO}

@app.get("/ask")
async def ask(question: str):
    if qa_chain is None:
        return {"erro": "O sistema não foi inicializado."}
    
    try:
        print(f"Java perguntou: {question}")
        result = qa_chain.invoke({"input": question})
        return {"resposta": result["answer"]}
    except Exception as e:
        print(f" Erro ao processar pergunta: {str(e)}")
        return {"erro": str(e)}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)