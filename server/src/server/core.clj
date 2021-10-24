(ns server.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer :all]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.util.response :refer [response]]
            [cheshire.core :refer [generate-string]])
  (:gen-class))


(defn scramble [str1 str2]
  "returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false"
  ; takes de frequencies of the strings, and compare. if the str2 (target string) has a letter with frequency superior to str1 (source string), then,
  ; it cannot be rearrenged. otherwise, it can.
  ; expected only lower strings as input
  (let [is-a-valid-input? (and (re-matches #"[a-z]+" str1) (re-matches #"[a-z]+" str2))
        source-str-freq (frequencies str1)
        target-str-freq (frequencies str2)]
    (if is-a-valid-input?
      (every? false? (map #(if (get source-str-freq %)
                             (> (get target-str-freq %) (get source-str-freq %))) (keys target-str-freq)))
      {:error "Invalid input. Expected only a-z lower characters."})))

(defn endpoint-scramble [req]
  (let [req-body (:body req)
        str1 (:str1 req-body)
        str2 (:str2 req-body)
        scramble? (scramble str1 str2)]
    {:status  (if (:error scramble?) 400 200)
     :headers {"Content-Type" "application/json"
               "Access-Control-Allow-Origin" "*"
               "Access-Control-Allow-Headers" "*"}
     :body  (generate-string {:scramble scramble?})}))

;; (defroutes server-routes
;;   (wrap-json-body
;;    (POST "/" [] endpoint-scramble) {:keywords? true}))

(defroutes server-routes
  (-> (wrap-json-body
       (POST "/" [] endpoint-scramble) {:keywords? true})
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:post :options])))

(defn -main
  [& args]
  (run-server server-routes {:port 3333})
  (println "Server started at PORT 3333"))