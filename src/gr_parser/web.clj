(ns gr-parser.web
  (:require [compojure.core :refer :all]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer :all]))

(defn success-response [body]
  {:body body
   :status 200
   :headers {"Content-Type" "application/json"}})

(defonce people-db (atom []))

(defroutes app
  (POST "/records" []
        (success-response "Ok"))
  (GET "/records/gender" []
       (success-response "gender"))
  (GET "/records/birthdate" []
       (success-response "birthdate"))
  (GET "/records/name" []
       (success-response "name")))

(def wrapped-app (wrap-defaults app api-defaults))

(defonce server (jetty/run-jetty #'wrapped-app {:join? false :port 8080}))
