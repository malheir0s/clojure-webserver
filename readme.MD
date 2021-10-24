# First, need to build image. on root directory of the project:
## > docker build -t image-name .

# Then, run the container:
## > docker run --rm -it -p 3333:3333 -p 8280:8280 -p 8290:8290 -p 9630:9630 -p 8777:8777 image-name bash
## on windows, replace $(pwd) with %cd%

# > cd server
## Running the tests:
## > lein test

## Running the server:
## > lein run

# Running the client:
## Open another instance of the container:
## > docker exec -it <container_id> bash
## > cd client
## > npm run watch

## Server runs at port 3333.  Client runs at localhost:8280, and shadow-cljs server at localhost:9630 
