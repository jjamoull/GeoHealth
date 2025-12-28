## Commit conventions
To commit your changes correctly, here is the general template you must follow for each commit, it must be written in English :
```
Summary : 

<type> : Short description of your changes

Description : 
Changed file : The file(s) that you changed
Detailed description of what you did
```
Type : 
- feat : Commits that add, adjust or remove a new feature to the API or UI
- fix : Commits that fix an API or UI bug of a preceded feat commit
- style : Commits that address code style (e.g., white-space, formatting, missing semi-colons) and do not affect application behavior
- test : Commits that add missing tests or correct existing ones
- docs : Commits that exclusively affect documentation
- build : Commits that affect build-related components such as build tools, dependencies, project version, CI/CD pipelines,...
- ops : Commits that affect operational components like infrastructure, deployment, backup, recovery procedures, ...
- chore : Commits that represent tasks like initial commit, modifying .gitignore, ...

### Example :

```
Summary : 
feat : Add user registration form

Description : 
Changed file : src/components/RegisterForm.py
Adding a registration form for the user authentification
```
```
Summary : 
fix : Prevent crash when submitting empty login form

Description : 
Changed file : src/components/LoginForm.py
Fixed a bug that caused the crash when submitting the login form
```
```
Summary : 
style : Reformating the login file

Description : 
Changed file : src/components/LoginForm.py
Reformating the entire file to follow the projet coding standards
```

```
Summary : 
test : add unit tests for user validation

Description : 
Changed file : tests/userValidation.test.py
Test the validation of user input
```

```
Summary :
docs : Update the login method information in README

Description : 
Changed file : README.md
Update the wrong explanation of the login method 
```

```
Summary :   
build : Update project dependencies

Description : 
Changed file : package.json
Update dependencies to their lated versions
```

```
Summary :   
ops : Configure a backup for the database

Description : 
Changed file :scripts/backup_db.sh
Added an automated script to prevent problems and ensure data recovery 
```

```
Summary :   
chore : Add gitignore

Description : 
Changed file : .gitignore
Added a gitignore file
```
