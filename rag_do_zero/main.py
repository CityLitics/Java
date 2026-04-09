from fastapi import FastAPI
from pypdf import PdfReader
import requests

app = FastAPI()

# 🔹 1. Ler PDF
def ler_pdf():
    reader = PdfReader("documento.pdf")
    texto = ""
    
    for page in reader.pages:
        texto += page.extract_text() or ""
    
    return texto

# 🔹 2. Quebrar em pedaços (chunks)
def dividir_texto(texto, tamanho=500):
    return [texto[i:i+tamanho] for i in range(0, len(texto), tamanho)]

# 🔹 3. Buscar partes relevantes (RAG simples)
def buscar_contexto(pergunta, chunks):
    relevantes = []

    for chunk in chunks:
        for palavra in pergunta.lower().split():
            if palavra in chunk.lower():
                relevantes.append(chunk)
                break

    return " ".join(relevantes[:3])  # pega até 3 partes

# 🔹 4. Perguntar ao modelo
def perguntar(pergunta):
    texto = ler_pdf()
    chunks = dividir_texto(texto)
    contexto = buscar_contexto(pergunta, chunks)

    prompt = f"""
    Responda com base no contexto abaixo:

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

# 🔹 Endpoint
@app.get("/ask")
def ask(question: str):
    return {"resposta": perguntar(question)}