angular.module('mongoExercise', [])
        .controller('tweets', function ($scope, $http) {

            $scope.resultList = [];
            $scope.login = {};
            $scope.register = {};
            $scope.loginAttemptMade = false;
            $scope.registerAttemptMade = false;

            $scope.getDistinct = function () {
                $http.get("http://localhost:8084/MongoTweet/api/twitter/distinct").
                        then(function (response) {
//                            $scope.resultList = [];
                            $scope.resultList = response.data;
                        });
            };

            $scope.getTopUsers = function () {
                $http.get("http://localhost:8084/MongoTweet/api/twitter/topusers")
                        .then(function (response) {
                            $scope.resultList = response.data;
                        });
            };
            
            $scope.getGrumpiest = function () {
                $http.get("http://localhost:8084/MongoTweet/api/twitter/grump")
                        .then(function (response) {
                            $scope.resultList = response.data;
                        });
            };
            
            $scope.getHappiest = function () {
                $http.get("http://localhost:8084/MongoTweet/api/twitter/happy")
                        .then(function (response) {
                            $scope.resultList = response.data;
                        });
            };
            
            $scope.getTopMentioned = function () {
                $http.get("http://localhost:8084/MongoTweet/api/twitter/mentioned")
                        .then(function (response) {
                            $scope.resultList = response.data;
                        });
            };
            
            $scope.getTopMentioning = function () {
                $http.get("http://localhost:8084/MongoTweet/api/twitter/mentioning")
                        .then(function (response) {
                            $scope.resultList = response.data;
                        });
            };

        });