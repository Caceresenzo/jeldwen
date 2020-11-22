#!/bin/bash

microk8s status --wait-ready

microk8s enable registry dashboard dns
echo 127.0.0.1-127.0.0.1 | microk8s enable metallb

mkdir -p ~/.kube
microk8s config > ~/.kube/config