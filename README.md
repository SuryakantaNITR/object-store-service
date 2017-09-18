## Build distribution

    1. Clean and Build
        cd {project_root_directory}
        gradle clean
        gradle zip
        
    2. Get the distributions file
        cd {project_root_directory}/build/distributions/
        cp object-store-service-0.0.1.zip {remote_server_path}

## Prerequisites to run in remote machine

    1. Install MongoDB
        Ubuntu: apt-get install mongodb
        CentOS: yum install mongodb-org-server-2.6.12-1.x86_64
        
    2. Run Mongo Server on 27017 port
        service mongod start
    
    3. Start mongo from console and create database
        use object-store

## Run distribution file
    
    1. Run as background daemon
        unzip object-store-service-0.0.1.zip
        nohup java -jar object-store-service-0.0.1.jar &
        
