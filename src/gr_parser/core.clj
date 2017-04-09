(ns gr-parser.core
  (:require [clojure.string :as str])
  (:refer-clojure :exclude [sort]))

(def delimitters
(def ^:const delimitters
  "A map of the record's type of delimitter to a regex to split the
  record"
  {:pipe  #" \| "
   :comma #", "
   :space #" "})

(def fields [:last-name :first-name :gender :favorite-color :date-of-birth])
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

(defmulti sort (fn [k people] k))

(defmethod sort [:gender :last-name]
  [_ people]
  (sort-by (juxt :gender :last-name) people))

(defmethod sort :date-of-birth
  [_ people]
  (sort-by :date-of-birth people))

;; last name descending
(defmethod sort :last-name
  [_ people]
  (reverse (sort-by :last-name people)))

