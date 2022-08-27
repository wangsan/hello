## Http Sample
```shell
# test simple
curl http://localhost:8080/get

# test circuit breaker
curl --dump-header - --header 'Host: www.circuitbreaker.com' http://localhost:8080/delay/3
```


## Websocket Sample

[install wscat](https://www.npmjs.com/package/wscat)

In one terminal, run websocket server:
```
wscat --listen 9000
``` 

In another, run a client, connecting through gateway:
```
wscat --connect ws://localhost:8080/echo
```

type away in either server and client, messages will be passed appropriately.
