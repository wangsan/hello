#
# Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
#

# test simple
curl http://localhost:8080/get

# test circuit breaker
curl --dump-header - --header 'Host: www.circuitbreaker.com' http://localhost:8080/delay/3
