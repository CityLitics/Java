from fastapi import FastAPI
from pypdf import PdfReader
import requests

app = FastAPI()

# >> bloco de leitura do pdf << 
def ler_pdf():
    reader = PdfReader("documento.pdf")
    texto = ""
    
    for page in reader.pages:
        texto += page.extract_text() or ""
    
    return texto

# >> SEPARAÇÃO DOS CHUNKS , possivel definir o tamanho de saida<< 
def dividir_texto(texto, tamanho=500):
    return [texto[i:i+tamanho] for i in range(0, len(texto), tamanho)]

# busca de partes importantes do doc <<<< 
def buscar_contexto(pergunta, chunks):
    relevantes = []

    for chunk in chunks:
        for palavra in pergunta.lower().split():
            if palavra in chunk.lower():
                relevantes.append(chunk)
                break

    #  >> se nada for encontrado << 
    if not relevantes:
        return " ".join(chunks[:3])

    return " ".join(relevantes[:3])

 # >>> onde é feita a pergunta <<< 
def perguntar(pergunta):
    texto = ler_pdf()
    chunks = dividir_texto(texto)
    contexto = buscar_contexto(pergunta, chunks)

    prompt = f"""
Responda baseado no contexto abaixo.
Se não encontrar resposta exata, faça um resumo do conteúdo.

CONTEXTO:
{contexto}

PERGUNTA:
{pergunta}
"""

    response = requests.post(
        "http://localhost:11434/api/generate",
        json={
            "model": "llama3.2:1b",
            "prompt": prompt,
            "stream": False
        }
    )

    return response.json()["response"]

@app.get("/ask")
def ask(question: str):
    return {"resposta": perguntar(question)}