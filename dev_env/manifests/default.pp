    exec { 'update_apt':
        command => "/usr/bin/apt-get update"
    } ->

    package { ['mariadb-server', 'mysql-client']:
        ensure => installed
    } ->    

    file { '/vagrant/db/configure.sh':
        mode => 775
    } ->

    exec { 'configure_db': 
        user    => 'root',
        command => '/vagrant/db/configure.sh'
    } ->

    package { ['openjdk-8-jre', 'openjdk-8-jdk']:
        ensure => installed,
    } 