#!/bin/bash

echo -n "Are you sure? [y/N] "
read REPLY

if [[ $REPLY =~ ^[Yy]$ ]]
then
	kubectl delete -k .
else
	echo Cancelled
fi