# 🚀 DD2480 Project Overview

This project is an implementation of the DECIDE() function, which is part of a hypothetical anti-ballistic missile system. The function will assess radar tracking data and determine whether an interceptor should be launched based on specific conditions. Written in Java, the DECIDE() function evaluates a series of Logical Connector Matrices (LCM) and Conditions Met Vectors (CMV) to decide whether to issue a launch-unlock signal.

## 🛠️ Core Components:

- **Conditions Met Vector (CMV)**: A vector of boolean values that represents the truth of 15 different Launch Interceptor Conditions (LICs) based on radar data.

- **Logical Connector Matrix (LCM)**: A 15x15 matrix that defines how individual LICs must be combined. Elements in this matrix can be ANDD, ORR, or NOTUSED.

- **Preliminary Unlocking Matrix (PUM)**: A matrix derived from the LCM and CMV that represents preliminary combinations of LIC conditions.

- **Preliminary Unlocking Vector (PUV)**: A vector that determines which LICs matter for the final decision process.

- **Final Unlocking Vector (FUV)**: A vector where all elements must be true for the interceptor launch-unlock signal to be issued.

## 📂 Project Structure:

```console
/DD2480
├── /.github                  # CI/CD in GitHub
├── /src
│   ├── /main
│   │   └── /decide
│   │       ├── /application  # Main-function
│   │       ├── /core         # Core-functions
│   │       ├── /model        # Parameters etc
│   │       └── /util         # Help-functions
│   ├── /test
│   │   ├── /decide
|   |   |   ├── /decider      # Test entire program
|   |   |   └── /lic_judge    # LIC Tests
│   │   └── /resources        # Test input-files
├── /target                   # Generated class-files etc
├── /.gitignore
├── /pom.xml                  # mvn config
├── /docs                     # Project documentation
└── README.md                 # Project overview


```

## ⚙️ Running the Project with Maven

### Prerequisites

Before running the project, ensure you have Maven installed. If you haven’t installed it yet, follow the instructions [here](https://maven.apache.org/install.html).

### Build the Project

To compile and package the project, run:

```console
mvn clean install
```

### Run the Application

Execute the following command to start the application:

```console
mvn exec:java -Dexec.mainClass=decide.application.DeciderApplication
```

### Running Tests

#### Run All Tests

To execute all test cases, use:

```console
mvn test
```

#### Run single test

To run a specific test class, for example LIC0 use:

```console
mvn test -Dtest=JudgeLic0Test
```

## 🤝 Statement of contributions

Everyone has been active in this group project, and the collaboration has been excellent. From the beginning, we discussed each other's skills and divided the work accordingly, ensuring that everyone could contribute in ways that played to their strengths.

### Contributors:

- Giacomo Ricco
- Kohei Kuramoto
- Max Linghag Ahlgren
- Simon Li
- Yuhang Lin

### Specific contributions to the project:

```console
- Deciding tools                       # (All)
- Code skeleton and setup              # (Lin)
- LIC0                                 # (Max)
- LIC1                                 # (Simon)
- LIC2                                 # (Simon)
- LIC3                                 # (Kohei)
- LIC4                                 # (Kohei)
- LIC5                                 # (Max)
- LIC6                                 # (Giacomo)
- LIC7                                 # (Giacomo)
- LIC8                                 # (Giacomo)
- LIC9                                 # (Simon)
- LIC10                                # (Max)
- LIC11                                # (Max)
- LIC12                                # (Simon)
- LIC13                                # (Simon)
- LIC14                                # (Max)
- Decide test                          # (TBC)
- CMV                                  # (Lin)
- LCM                                  # (Lin)
- PUM                                  # (Lin)
- PUV                                  # (Lin)
- FUV                                  # (Lin)
- "Way of working" docs                # (Giacomo & Kohei)
- PowerPoint                           # (Giacomo & Kohei)
- README                               # (Max)
```

## 📜 License

This project is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License). You are free to use, modify, and distribute this project for personal or commercial purposes.

Conditions:

- You may use and test the code freely.
- You must include the original license and copyright notice in all copies or substantial portions of the software.
- This software is provided "as is", without warranty of any kind.
