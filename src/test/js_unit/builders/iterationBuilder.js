iterationBuilder = function(){
    var builder = {
        data: {}
    };

    builder.withIterationNumber = function(iterationNumber){
        builder.data.iterationNumber = iterationNumber;
        return builder;
    }

    builder.withDescription = function(description){
        builder.data.description = description;
        return builder;
    }

    builder.withStartDate = function(startDate){
        builder.data.startDate = startDate;
        return builder;
    }

    builder.withEndDate = function(endDate){
        builder.data.endDate = endDate;
        return builder;
    }

    builder.build = function(){
        return builder.data;
    }

    return builder;
}