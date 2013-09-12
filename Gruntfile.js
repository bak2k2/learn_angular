module.exports = function(grunt) {
    grunt.loadNpmTasks('grunt-karma');

    grunt.initConfig({
        karma: {
            options: {
                configFile: 'src/test/karma.conf.js',
                runnerPort: 9999,
                browsers: ['PhantomJS']
            },
            continuous: {
                singleRun: true,
                browsers: ['PhantomJS']
            },
            dev: {
                reporters: 'dots'
            }
        }
    });

    grunt.registerTask('default', 'karma:dev');
}