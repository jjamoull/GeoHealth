# Contribute to this project

## 1.What is this document ?

If you are here, it means that you want to contribute to this project (thank you!).

In this document, you will find all the guidelines you must follow to contribute correctly to the repository. The following sections are covered:

- **Branch Management Policy**: explains how you should manage your branches
- **Commit Policy**: explains which commit message standards you should follow
- **Review Template**: explains which template you should use when performing a review
- **Code Conventions**: explains the conventions you should follow when writing code

Note that we try to be as exhaustive as possible, but it is not possible to define rules for every situation. If no rule applies to a specific case, please look at how other contributors work and follow general best practices.

## 2 Branch management policy


## 2.1 General Explanation

To handle all branches, we will follow the **trunk-based development policy**. The idea is to have only one master branch (the "main" branch in our case) and to create new branches for each new feature by forking the "main" branch.

All branches must be used **only to implement one feature** and **shouldn't live longer than a couple of days**. To ensure that merges do not break the build, all branches are automatically analyzed by *SonarQube*, *Checkstyle*, and tested automatically when creating a pull request.

Here are the main steps you should follow when you want to add a new feature:

1. **Create a new branch** by forking the "main" branch. Please refer to the **Branch Name Policy** to name your branch correctly.
2. **Start coding your feature** on your branch. Don’t forget to commit your code and test it regularly.
3. Whenever you are satisfied with your work, **create a pull request** on the "main" branch.
4. **Wait for a reviewer** to validate your pull request.  
   *(Note: if SonarQube, Checkstyle, or automated tests fail, your code won't be reviewed.)*
5. You will either have to **make the changes requested by the reviewer**, or your code will be merged.


### 2.2 Branch name policy
To name the branch correctly, here is the template you must follow for each type of contribution :

For features (new functionality) :

`feature/name-of-the-feature`

example : 

`feature/authentification`

For bugfix :

`bugfix/name-of-the-fixed-problem`

example : 

`bugfix/null-pointer-exception-fixed`

For documents :

`doc/part-of-documentation-updated`

example : 

`doc/issue-part-updated`

In general, make sure you have only lower case and Hyphens-separeted word, use only one hyphen and do not put it at the end of the name. Use only alphanumeric character and try to make as descriptive and concive name as possible.


### 2.3 Where does a branch shoud be placed 
After cloning the repository, the new branch is created from "main" and can evolve independently.
### 2.4 When a branch can be merged
You want your branch to be merged but you don’t know what the necessary steps are. Here is the checklist:

- All automated tests have passed
- Checkstyle has passed
- The SonarQube analysis shows that the coverage is higher than 70%
- All changes requested by the reviewer have been made (or at least a decision has been taken if the change asked was not relevant)
- At least one reviewer other than you has approved your branch

If any of these steps is not completed, your branch won’t be merged.
## 3 Commit conventions
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


## 4 Review conventions 

### 4.1 Goal
Code reviews aim to:

- Maintain high code quality and consistency
- Detect bugs and security issues early
- Improve readability and maintainability
- Share knowledge across the team
- Encourage constructive collaboration
All reviews should be respectful, constructive, and focused on the code, not the author.

### 4.2 When should a PR be reviewed ? 

A pull request is ready for review once: 
- The code compiles and all tests pass
- The PR is focused on one single feature or concern
- The description of the PR clearly explains _what_ is does as well as _why_ it is relevant to the project
- No more unfinished work remains (no more todo's)

### 4.3 PR Guidelines

#### Title
The title of a PR should always follow this template: 

\<type\> : \<short description\>

Examples : 
- fix: prevent null pointer in user service
- feat: add PDF export
- docs: updated documentation with last added feature

#### Description 
Each PR should include :
- **Context**: why is this change needed
- **Summary** of the changes
- **Tests performed**
- (**Related issues)**: if any

The content of a PR should always follow this template:
##### Context
Explain the motivation of this PR
##### Changes 
Bullet list of main changes
##### Testing
- Bullet list of unit tests performed
- Bullet list of manuel tests performed
##### Related Issues



### 4.4 Review Template
(For all four points, see 4.5)
#### Code quality
Review of the code quality
#### Code Correctness
Review of the code correctness
#### Tests
Review of the tests
#### Performance and Security
Review of the performance and security

### 4.4 Responsibilities of the reviewer 
Reviewers should: 
- Review PRs as soons as possible
- Focus on the code, not its author
- Ask questions instead of making assumptions
- Suggest improvements, not demands
- Only approve a PR when 100% confident that the code is ready

### 4.5 What to review ?

#### Code quality
- Is it readable ?
- Is it well-structured ?
- Are names (functions, variables,...) meaningful and consistent ?
- Is the logic easy to understand ?
- Are all functions well specified ?

#### Code correctness
- Does the code do what it claims to do ?
- Are edge cases handled ?
- Are error cases handled ?

#### Tests 
- Are tests present and relevant ?
- Do they cover most important scenarios ?
- Are they readable ?

#### Performance and secrurity 
- Any obvious performance bottlenecks?
- Any security concerns ?

### 4.6 Approval guidelines 
A PR may be merged once: 
- All required reviewers have approved it
- All requested changes have been done
- CI checks are passing
- The PR is up-to-date with the target branch

### 4.7 Disagreements
If a disagreement occurs:
- Discuss it in the PR comments
- Provide technical arguments
- Prefer consistency over personal preference


## 5 Code conventions to respect

### 5.1 Main Principles

- Code must be clear, readable, and maintainable
- Prefer simplicity over cleverness
- Follow the principle of least surprise
- Consistency is more important than personal preference

### 5.2 Naming Conventions

- Use meaningful and descriptive names
- Avoid abbreviations unless they are widely accepted
- Names should reflect intent, not implementation
Examples

- Functions: getUserById, parseCertificate
- Variables: userCount, certificatePath
- Booleans: isValid, hasAccess

### 5.3 Formatting and Style

- Respect the project’s formatting rules (linters / formatters)
- Keep line length reasonable
- Use consistent indentation
- One logical instruction per line

### 5.4 Functions and Methods

- Functions should do one thing only
- Keep functions short and focused
- Avoid deeply nested logic
- Prefer early returns over long if/else chains

5.5 Comments and Documentation

- Code should be self-explanatory
- Comments should explain why, not what
- Remove outdated or redundant comments
- Public APIs must be documented

### 5.6 Error Handling

- Handle errors explicitly
- Never silently ignore errors
- Provide meaningful error messages
- Use appropriate exception/error types

### 5.7 Tests

- New features must include tests
- Bug fixes must include regression tests
- Tests should be:
  - Readable
  - Deterministic
  - Independent

### 5.8 Performance and Simplicity

- Avoid premature optimisation
- Do not introduce unnecessary complexity
- Prefer readable solutions unless performance is critical
- Justify complex logic with comments

### 5.9 Security 

- Never hardcode secrets or credentials
- Always validate external inputs
- Follow least-privilege principles ([PoLP](https://en.wikipedia.org/wiki/Principle_of_least_privilege))
- Be cautious with file systems and network operations

## 6 Dummy issues explanations

Dummy issues are artificial or simplified issues created for educational, testing, or demonstration purposes.
They are not real bugs or feature requests, but are used to practice workflows such as issue tracking, pull requests, and code reviews.

### 6.1 Purpose of Dummy Issues

Dummy issues are used to:
- Learn and practice GitHub workflows
- Simulate real-world development scenarios
- Test review and contribution conventions
- Provide examples for students or contributors
- They help contributors focus on process and collaboration, rather than production impact.

### 6.2 Identification of Dummy Issues

Dummy issues must be clearly identifiable.
They should:
- Contain the label dummy
- Include [DUMMY] in the issue title
- Mention explicitly that the issue is artificial in the description
Example title:

- [DUMMY] Fix null pointer in certificate service

### 6.3 Content of a Dummy Issue

A dummy issue should include:
- A short description of the fictional problem
- Clear and simple steps to reproduce it
- Expected vs actual behavior (even if fictional)
- Scope limitations (what is intentionally ignored)
- Dummy issues should remain simple and focused on a specific aspect/feature

### 6.4 How Dummy Issues Should Be Treated

When working on a dummy issue:
- Follow the same conventions as real issues
- Create a proper branch and pull request
- Respect all review and code conventions
- Treat feedback seriously
- Even though the issue is dummy, use the real workflow

### 6.5 What Dummy Issues Should NOT Be Used For

Dummy issues must not be used to:
- Track real bugs or production problems
- Introduce breaking or unsafe changes
- Bypass review or quality standards
- Justify poor or incomplete implementations

### 6.6 Closing Dummy Issues

A dummy issue can be closed when:
- The associated pull request is merged
- The learning objective is achieved
- The example has been sufficiently demonstrated
- The closing comment should mention that the issue was a dummy exercise.

### 6.7 Dummy Issues in Reviews

Reviewers should:

- Apply the same review standards as for real issues
- Focus on conventions, clarity, and correctness
- Avoid over optimising or over engineering a dummy issue
- The goal is learning and consistency, not perfection.
