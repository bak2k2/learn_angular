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