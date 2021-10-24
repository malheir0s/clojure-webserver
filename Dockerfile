FROM clojure:openjdk-11-tools-deps-1.10.3.943
WORKDIR /work

RUN curl -sL https://deb.nodesource.com/setup_12.x | bash - \
 && apt-get install -y nodejs \
 && apt-get install -y leiningen \
 && apt-get clean

COPY . .

WORKDIR /work/server
RUN lein deps

WORKDIR /work/client
RUN npm install -f
RUN npx shadow-cljs

WORKDIR /work

