This is a web application that summarizes uploaded PDF or Word documents using OPENAI GPT.
It also provides a chatbot that answer user questions based on the document summary.

Before running the project, ensure you have the following installed:
- JAVA 17 or later
- Maven
- MySQL
- OpenAI API Key

Frontend Requirements:
- Node.js
- npm

Backend setup(Spring Boot):
- cd document-summarizer
- Create a MySQL database named "document"
- Update the "application.properties" located in resources file with your MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/document_summarizer
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
- Replace your OPENAI_API_KEY in ChatbotService.java and SummarizationService.java with your openai api key.
- mvn clean install
- mvn spring-boot:run

Frontend setup(React):
- cd frontend/document-summarizer-frontend
- npm install
- npm start
- The front end will start at http://localhost:3000

How to use:
- Go to http://localhost:3000
- Upload a PDF and chatting with the chatbot

See the demo video for code walk through and basic usage.
