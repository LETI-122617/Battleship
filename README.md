# Battleship

Basic academic version of Battleship game to build upon.

LETI-122608 Eduardo Real
LETI-122617 Tomás Cerveira
LETI-110331 Francisco Monteiro


This workflow, named “Java CI with Maven”, is triggered whenever code is pushed to the main branch or a pull request targets main. Its purpose is to automatically build and test the Java project using Maven.

Job: build

The workflow defines a single job called build, which runs on the GitHub-hosted ubuntu-latest environment.

Step-by-step actions inside the job

1. actions/checkout@v4
	•	Checks out the repository’s code to the runner.
	•	Required so Maven can access the pom.xml and source files.

2. Set up JDK 17
     - name: Set up JDK 17
       uses: actions/setup-java@v4
   • Installs Java 17 on the runner using the Temurin distribution.
	 • Enables Maven caching, speeding up dependency downloads in future runs.

3. Run tests
       run: mvn -B package --file pom.xml
   • Executes mvn package, which:
   • Compiles the project,
	 • Runs all unit tests (Maven’s test phase),
   • Packages the compiled code.
	 • This step ensures the build succeeds and the tests pass.

4. Update dependency graph (optional)
   • Submits the project’s Maven dependency graph to GitHub.
	 • Improves Dependabot’s ability to detect vulnerabilities in external libraries.
	 • Does not affect the build or tests.
