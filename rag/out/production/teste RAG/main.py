import os
from fastapi import FastAPI
from langchain_community.document_loaders import PyPDFLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_ollama import OllamaEmbeddings, ChatOllama
from langchain_community.vectorstores import Chroma
from langchain.chains import RetrievalQA

app = FastAPI()

# --- CONFIGURAÇÃO INICIAL ---
NOME_ARQUIVO = "documento.pdf"
qa_chain = None  # Variável global para ser usada no endpoint /ask

print("--- Iniciando RAG Local com Ollama (Versão Leve 1B) ---")

# 1. Carregamento e Divisão
if not os.path.exists(NOME_ARQUIVO):
    print(f"AVISO: {NOME_ARQUIVO} não encontrado.")
    chunks = []
else:
    print(f"Lendo o arquivo: {NOME_ARQUIVO}")
    loader = PyPDFLoader(NOME_ARQUIVO)
    data = loader.load()
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=300, chunk_overlap=50)
    chunks = text_splitter.split_documents(data)

# 2. Setup dos Modelos e Banco de Dados
try:
    if chunks:
        print("--- Criando banco vetorial (ChromaDB) ---")
        embeddings = OllamaEmbeddings(model="mxbai-embed-large")
        llm = ChatOllama(model="llama3.2:1b") # Versão super leve para evitar crash
        
        vector_db = Chroma.from_documents(
            documents=chunks,
            embedding=embeddings,
            persist_directory="./db_local"
        )
        
        # 4. Criação da Corrente de Resposta
        qa_chain = RetrievalQA.from_chain_type(
            llm=llm,
            chain_type="stuff",
            retriever=vector_db.as_retriever()
        )
        print("--- TUDO PRONTO: Sistema de busca ativo! ---")
except Exception as e:
    print(f"❌ ERRO CRÍTICO NA INICIALIZAÇÃO: {str(e)}")

# --- ENDPOINTS (Para o Java chamar) ---

@app.get("/")
def home():
    return {"status": "Online", "documento": NOME_ARQUIVO}

@app.get("/ask")
async def ask(question: str):
    if qa_chain is None:
        return {"erro": "O sistema não foi inicializado. O PDF foi lido corretamente?"}
    
    try:
        print(f"🤖 Java perguntou: {question}")
        # Usamos invoke para as versões mais novas do LangChain
        result = qa_chain.invoke({"query": question})
        return {"resposta": result["result"]}
    except Exception as e:
        print(f"❌ Erro ao processar pergunta: {str(e)}")
        return {"erro": "A IA falhou ao responder. Verifique se o Ollama está aberto."}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)