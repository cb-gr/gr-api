(ns gr-parser.web
  (:require [compojure.core :refer :all]
            [cheshire.core :as json]
            [gr-parser.core :as gr-parser]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer :all]))

(defn success-response [body]
  {:body (json/generate-string body)
   :status 200
   :headers {"Content-Type" "application/json"}})

(defonce people-db (atom []))

(defn make-app [db]
  (wrap-defaults
   (routes
    (POST "/records" {{:keys [delimitter data]} :params}
          (let [delimitter (keyword delimitter)
                person (gr-parser/parse-line (gr-parser/delimitters delimitter)
                                             data)]
            (swap! db conj person)
            (success-response "Ok")))
    (GET "/records/gender" []
         (success-response
          (map gr-parser/format-record
               (gr-parser/sort [:gender :last-name] @db))))
    (GET "/records/birthdate" []
         (success-response
          (map gr-parser/format-record
               (gr-parser/sort :date-of-birth @db))))
    (GET "/records/name" []
         (success-response
          (map gr-parser/format-record
               (gr-parser/sort :last-name @db)))))
   api-defaults))

(def wrapped-app (make-app people-db))

(defonce server (jetty/run-jetty #'wrapped-app {:join? false :port 8080}))
