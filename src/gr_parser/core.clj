(ns gr-parser.core
  (:require [clojure.string :as str]))

(def delimitters
(def ^:const delimitters
  "A map of the record's type of delimitter to a regex to split the
  record"
  {:pipe  #" \| "
   :comma #", "
   :space #" "})

(def fields [:first-name :last-name :gender :favorite-color :date-of-birth])
(defrecord Person [first-name last-name gender favorite-color date-of-birth])

(def date-of-birth-format (java.text.SimpleDateFormat. "MM/dd/yyyy"))

(defn parse-date-of-birth
  [dob]
  (.parse date-of-birth-format dob))

(defn person
  "Constructs a Person with date-of-birth parsed from a map"
  [p]
  (-> p
      (update :date-of-birth parse-date-of-birth)
      (map->Person)))

(defn parse-line
  [delimitter-regex line]
  (->> (str/split line delimitter-regex)
       (zipmap fields)
       (person)))


