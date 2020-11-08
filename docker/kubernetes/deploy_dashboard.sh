#!/bin/bash

kubectl delete -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml

TOKEN=$(kubectl -n kube-system describe secret default | grep "token:" | awk '{ print $2 }')
kubectl config set-credentials docker-for-desktop --token="${TOKEN}"

echo
echo $TOKEN
echo
echo http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
echo

kubectl proxy