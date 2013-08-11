function WordcloudTrendCtrl($scope, $http) {
    wordlist = {};
    url = '/resources/project/retro';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            wordlist = data.wordCounts;
            $scope.word_trend_list = wordlist;
            $("#wordtestdiv").jQCloud(wordlist);
        });
}