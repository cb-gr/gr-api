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
(defrecord Person [last-name first-name gender favorite-color date-of-birth])

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

(def person-formatter
  "Provides a mapping of field to a function that formats the field"
  {:date-of-birth
   (fn [dob]
     (.format date-of-birth-format dob))})

(defn format-record
  [person]
  (reduce-kv (fn [m k v]
               (let [transform (get person-formatter k identity)]
                 (assoc m k (transform v))))
             {}
             person))
(def console-format "%10s %10s %8s %20s %20s")

(defn print-formatted
  [format-str fields]
  (println (apply format format-str fields)))

(defn console-print
  [people]
  (print-formatted console-format (map name fields))
  (doseq [p people]
    (let [formatted-person (format-record p)]
      (print-formatted console-format
                       (for [f fields]
                         (get formatted-person f nil))))))
