# Prime Number Calculator

RESTfull service that calculates prime numbers up to Integer.MAX_VALUE.

## Supported functionality

* Verifies provided number type GET http://localhost:8080/api/v1/numbers/{numberToVerify}
    * accepts natural number up to Integer.MAX_VALUE
    * returns
    ```json
    {
        "number": "<numberToVerify>",
        "type": "<numberType>"
    }
    ```
    returned type is: UNIT for 1, PRIME for prime numbers, COMPOSITE for composite numbers 

* Returns list of primes in the range provided GET http://localhost:8080/api/v1/numbers/primes?lowerLimit=<lowerLimit>&upperLimit=<upperLimit>
    * accepts natural numbers as parameters up to Integer.MAX_VALUE
        * upperLimit parameter is mandatory
        * lowerLimit parameter is optional
        * maximum range provided cannot exceed 500k

    * returns:
    ```json
    {
        "lowerLimit":"<lowerLimit>",
        "upperLimit":"<upperLimit>",
        "primes": ["array of prime numbers in provided range"]
    }
    ```

## Management

* Running application: mvn spring-boot:run.
* Application listens on port 8080.
