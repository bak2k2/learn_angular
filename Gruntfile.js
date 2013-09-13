module.exports = function(grunt) {
    grunt.loadNpmTasks('grunt-karma');

    grunt.initConfig({
        karma: {
            options: {
                configFile: 'src/test/karma.conf.js',
                runnerPort: 9999
            },
            continuous: {
                singleRun: true,
                browsers: ['PhantomJS']
            },
            dev: {
                browsers: ['PhantomJS']
            },
            unit: {
                autoWatch: true,
                singleRun: false,
                browsers: ['PhantomJS']
            }
        },
        watch: {
            //run unit tests with karma (server needs to be already running)
            karma: {
                files: ['src/main/webapp/js/**/*.js', 'src/test/browser/**/*.js'],
                tasks: ['karma:unit:run'] //NOTE the :run flag
            }
        }
    });

    grunt.registerTask('default', 'karma:dev');
}