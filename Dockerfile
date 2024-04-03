FROM ubuntu:latest
LABEL authors="dimitriaigle"

ENTRYPOINT ["top", "-b"]