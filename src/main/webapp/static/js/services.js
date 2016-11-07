angular.module('springPortfolio.services', [])
    //.constant('sockJsProtocols', ["xhr-streaming", "xhr-polling"]) // only allow XHR protocols
    .constant('sockJsProtocols', [])
    .factory('StompClient', ['sockJsProtocols', '$q', function (sockJsProtocols, $q) {
        var stompClient;
        var wrappedSocket = {
            init: function (url) {
                if (sockJsProtocols.length > 0) {
                    stompClient = Stomp.over(new SockJS(url, null, {transports: sockJsProtocols}));
                }
                else {
                    stompClient = Stomp.over(new SockJS(url));
                }
            },
            connect: function (username, password) {
                return $q(function (resolve, reject) {
                    if (!stompClient) {
                        reject("STOMP client not created");
                    } else {
                        stompClient.connect(username, password, function (frame) {
                            resolve(frame);
                        }, function (error) {
                            reject("STOMP protocol error " + error);
                        });
                    }
                });
            },
            disconnect: function () {
                stompClient.disconnect();
            },
            subscribe: function (destination) {
                var deferred = $q.defer();
                if (!stompClient) {
                    deferred.reject("STOMP client not created");
                } else {
                    stompClient.subscribe(destination, function (message) {
                        deferred.notify(JSON.parse(message.body));
                    });
                }
                return deferred.promise;
            },
            subscribeSingle: function (destination) {
                return $q(function (resolve, reject) {
                    if (!stompClient) {
                        reject("STOMP client not created");
                    } else {
                        stompClient.subscribe(destination, function (message) {
                            resolve(JSON.parse(message.body));
                        });
                    }
                });
            },
            send: function (destination, headers, object) {
                stompClient.send(destination, headers, object);
            }
        };
        return wrappedSocket;
    }])
    .factory('loginService', function ($http) {

        return {
            login : login
        };

        function login(userDTO){
            return $http.post(userDTO);
        }

    })
    //.factory('loginService', ['StompClient', '$q', function (StompClient, $q) {
    //
    //    return {
    //        connect: function (username, password, url) {
    //            StompClient.init(url);
    //            return StompClient.connect(username, password).then(function (frame) {
    //                return frame.headers['user-name'];
    //            });
    //        },
    //        disconnect: function () {
    //            StompClient.disconnect();
    //        },
    //        fetchPositionUpdateStream: function () {
    //            return StompClient.subscribe("/user/queue/for-me");
    //        }
    //    };
    //
    //}]);
