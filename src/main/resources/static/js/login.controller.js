angular.module('myApp')
    .controller('loginCtrl',
    function (loginService, $cookies, StompClient) {
        var vm = this;
        vm.login = login;

        function login() {
            loginService.login(vm.userDTO,
                function (data) {
                    $cookies.put("token", data.data.token);
                    //StompClient.connectSocket();
                },
                function () {

                });
        }

    });