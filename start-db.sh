#!/bin/bash

docker -v
if [ $? -ne 0 ]; then
   sudo apt update
   sudo apt install docker
   sudo usermod -aG docker $USER
   newgrp docker
fi

docker run --name mysql                     \
            -p 3306:3306                    \
            -e MYSQL_ROOT_PASSWORD=Rahziec2 \
            -e MYSQL_DATABASE=bd2_grupo24   \
            -e MYSQL_USER=grupo24           \
            -e MYSQL_PASSWORD=eiquib5A      \
            -d mysql:5.7
