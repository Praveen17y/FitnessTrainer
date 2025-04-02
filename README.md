

# FitnessTrainer

## Overview
FitnessTrainer is a Java Spring Boot application designed to provide personalized exercise and diet recommendations. The application takes user inputs such as current fitness level and optional exercise preferences and suggests relevant workout routines and diet plans. It utilizes an H2 in-memory database for storing exercises and diet plans and integrates with an LLM (Groq Cloud) via Apache HTTP to fetch additional guidance.

## Features
- **User Input Handling**: Accepts user fitness levels and optional exercise preferences.
- **Exercise Recommendation**: Retrieves personalized exercise routines from the H2 database.
- **Diet Plan Suggestions**: Provides tailored diet recommendations.
- **LLM Integration**: Fetches exercise guidance and repetition details from an LLM.
- **JSP Frontend**: Displays recommendations and accepts user inputs.
- **JSON Response Handling**: Processes and presents LLM responses in a structured format.

## Tech Stack
- **Backend**: Java Spring Boot
- **Database**: H2 in-memory database
- **Frontend**: JSP (Java Server Pages)
- **LLM Integration**: Groq Cloud (via Apache HTTP)

## Installation & Setup
### Prerequisites
- Java 17+
- Maven
- IntelliJ IDEA (or any preferred IDE)

### Steps
1. **Clone the Repository**:
   ```sh
   git clone https://github.com/Praveen17y/FitnessTrainer.git
   cd FitnessTrainer
   ```
2. **Build the Project**:
   ```sh
   mvn clean install
   ```
3. **Run the Application**:
   ```sh
   mvn spring-boot:run
   ```
4. **Access the Application**:
   - Open a browser and navigate to `http://localhost:8080`

## API Endpoints
| Endpoint  | Method | Description |
|-----------|--------|-------------|
| `/recommend` | POST  | Accepts user input and returns recommendations |
| `/exercises` | GET   | Retrieves available exercises |
| `/diet` | GET   | Fetches diet plans |

## Future Enhancements
- Add user authentication for personalized profiles.
- Expand the database with more workout and diet options.
- Improve UI/UX with modern frontend frameworks.
- Introduce machine learning-based fitness suggestions.

## Contributing
Contributions are welcome! Please open an issue or submit a pull request.

## License
This project is licensed under the MIT License.

## Contact
For questions or suggestions, feel free to reach out via GitHub issues or connect with me at [GitHub Profile](https://github.com/Praveen17y).

