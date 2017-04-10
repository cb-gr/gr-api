(ns gr-parser.web
  (:require [compojure.core :refer :all]
            [ring.adapter.jetty :as jetty]))

(defn success-response [body]
  {:body body
   :status 200
   :headers {"Content-Type" "application/json"}})

(defroutes app
  (POST "/records" []
        (success-response "Ok"))
  (GET "/records/gender" []
       (success-response "gender"))
  (GET "/records/birthdate" []
       (success-response "birthdate"))
  (GET "/records/name" []
       (success-response "name")))

(defonce server (jetty/run-jetty #'app {:join? false :port 8080}))
