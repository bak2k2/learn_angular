function WordcloudTrendCtrl($scope, $http) {
    wordlist = {};
    url = '/resources/project/retro';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            wordlist = data.wordCounts;
//            $(document).ready(function () {
                $("#wordclouddiv").jQCloud(wordlist);
//            });
        });
}