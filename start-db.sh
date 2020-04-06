#!/bin/bash

docker -v
if [ $? -ne 0 ]; then
   sudo apt update
   sudo apt install docker.io
   sudo usermod -aG docker $USER
   newgrp docker
fi


if [ ! "$(docker ps -q -f name=mysql)" ]; then
    if [ "$(docker ps -aq -f status=exited -f status=created -f name=mysql)" ]; then
        # cleanup
        echo "El contenedor mysql está detenido, eliminando"
        docker rm mysql
    fi
    
    echo "Iniciando nuevo contenedor mysql"
    docker run --name mysql                 \
            -p 33306:3306                    \
            -e MYSQL_ROOT_PASSWORD=Rahziec2 \
            -e MYSQL_DATABASE=bd2_grupo24   \
            -e MYSQL_USER=grupo24           \
            -e MYSQL_PASSWORD=eiquib5A      \
            -d mysql:5.7

else 
    echo "El contenedor mysql ya está corriendo."

fi
