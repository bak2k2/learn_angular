function WordcloudTrendCtrl($scope, $http) {
    wordlist = {};
    url = '/resources/project/retro';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            wordlist = data.wordCounts;
                $("#wordclouddiv").jQCloud(wordlist);
        });
}