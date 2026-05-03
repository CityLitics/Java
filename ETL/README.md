## O que você precisa
- Java 21 instalado
- Maven instalado
- Credenciais de acesso ao S3 (fornecidas pelo laboratório AWS Academy)

## Como instalar o Maven (Linux)
Abra o terminal e execute:
sudo apt update
sudo apt install maven -y


Confirme a instalação:
mvn -version


A primeira linha deve mostrar a versão do Maven e o Java 21.

## Como configurar as credenciais
1. No portal do curso, clique em AWS Details e copie os valores de:
   - AWS Access Key ID
   - AWS Secret Access Key
   - AWS Session Token

2. Crie o arquivo de credenciais no seu computador:
mkdir -p ~/.aws
nano ~/.aws/credentials


3. Cole o conteúdo abaixo, substituindo pelas suas chaves:
[default]
aws_access_key_id = SUA_ACCESS_KEY
aws_secret_access_key = SUA_SECRET_KEY
aws_session_token = SEU_SESSION_TOKEN


4. Salve com Ctrl+O, Enter, depois saia com Ctrl+X.

## Como gerar o JAR
1. No terminal, vá até a pasta do projeto onde está o arquivo pom.xml. Exemplo:
cd ~/Downloads/JAR_Test/jar-s3


2. Execute o comando de empacotamento:
mvn clean package


O Maven vai compilar o código, baixar as dependências e gerar o JAR. Se tudo der certo, a mensagem final será BUILD SUCCESS.

3. O JAR estará em:
target/java-maven-1.0-SNAPSHOT.jar


## Como executar o JAR
Entre na pasta target e execute:
cd target
java -jar java-maven-1.0-SNAPSHOT.jar


O arquivo municipiose_saneamento.csv (caso o nome do arquivo do seu S3 for diferente, vai ser preciso colocar o nome certo no Java) será baixado do S3 e salvo na pasta atual.

## Principais classes
- src/main/java/school/sptech/Main.java – classe principal, executa o download.
- src/main/java/school/sptech/config/S3Config.java – cria o cliente S3.
- src/main/java/school/sptech/service/S3Service.java – encapsula a chamada ao S3.

## Se der erro de credenciais
A mensagem Unable to load credentials significa que as chaves expiraram. Volte ao portal do curso, copie as novas credenciais e atualize o arquivo ~/.aws/credentials.

## Observações
- As credenciais não ficam salvas no código nem no JAR.