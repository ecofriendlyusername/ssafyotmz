# 학습로그

## Nginx
- has one **master process** and several **worker processes.**
- employs event-based model and OS-dependent mechanisms to efficiently distribute requests among worker processes.

### Master process
- read and evaluate configuration, and maintain worker processes

### Worker processes
- do actual processing of requests.

### API gateway
[What does an API gateway do?](https://www.redhat.com/en/topics/api/what-does-an-api-gateway-do)
- an [API management](https://www.redhat.com/en/topics/api/what-is-api-management) tool that sits between a client and a collection of backend services.
- **acts as a reverse proxy** to accept all [application programming interface (API)](https://www.redhat.com/en/topics/api/what-are-application-programming-interfaces) calls, aggregate the various services required to fulfill them, and return the appropriate result.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d48efed2-e089-40f4-b222-9bcfb38ba6b4/Untitled.png)
