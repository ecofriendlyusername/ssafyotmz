# 학습로그

## Testing
- Is part of Continuous Delivery that assures quality at each stage of the delivery pipeline before moving on to the next stage. 

## Functional and non-functional tests
Functional tests ask questions like:
- Does this button work?
- Does one module work with another module?
- Does the user journey work from the start of the experience to the end?

## Examples of functional tests
- **Unit tests** test an individual unit of code for functionality. In the example below, we test a function, which is a block of code that does one task.
- **Integration tests** verify the functionality between 2 or more modules. This example tests the integration between the e-commerce storefront and the shopping cart module.
- **Smoke testing** is preliminary testing to reveal failures that can result in rejection of a release.
- **Acceptance testing** confirms your application is working according to a requirements specification. In this example, there's a requirement for a rewards system to work with an application. The tests check for the expected behavior of a rewards system.

### Unit Testing
- Checks that your code is working as expected
- called unit testing because you break down the functionality of your program into discrete testable behaviors that you can test as individual units.
- Has the greatest effect on the quality of your code when it's an integral part of your software development workflow.

**As soon as you write a function or other block of application code, create unit tests that verify the behavior of the code in response to standard, boundary, and incorrect cases of input data, and that check any explicit or implicit assumptions made by the code.**

**With test driven development, you create the unit tests before you write the code, so you use the unit tests as both design documentation and functional specifications.**


DevOps is an iterative cycle of building, testing, and releasing software in short iterations. A comprehensive testing environment helps each iteration of the DevOps loop strengthen the quality of the product. A weak testing phase can mean defects progress to release, and developers need to fix bugs while the product is live. Development teams fall on both sides of the testing spectrum.



### Manual tests
- If you've used an app on your device and reported a bug, you've carried out a manual test.
- slower and require an environment for the testers.
- suitable when opinion and nuance play a role, like user experience.
### Automated tests
- scripted in advance and executed by a machine, they compare an expected result with the actual result.
- near-instant and execute in the hundreds or thousands at runtime.
- check for functionality and make sure every line of code and feature works as intended.

[source1]https://octopus.com/blog/different-types-of-tests
[source2]https://learn.microsoft.com/en-us/visualstudio/test/unit-test-basics?view=vs-2022
