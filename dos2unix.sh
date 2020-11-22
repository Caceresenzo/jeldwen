#!/bin/bash

function on() {
	find $1 -exec dos2unix {} \;
}

on docker