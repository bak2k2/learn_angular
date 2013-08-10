'use strict';

function cloneData(objectToClone)
{
    return JSON.parse(JSON.stringify(objectToClone));
}

function iterationComparator(a, b) {
    return a.iterationNumber < b.iterationNumber;
}

function projectComparator(a, b) {
    return a.projectName > b.projectName;
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function isEmptyObject(obj) {
    var name;
    for (name in obj) {
        return false;
    }
    return true;
}