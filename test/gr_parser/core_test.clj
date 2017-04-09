(ns gr-parser.core-test
  (:require [clojure.test :refer :all]
            [gr-parser.core :refer :all]))

(def expected-record (map->Person {:last-name      "FirstName"
                                   :first-name     "LastName"
                                   :date-of-birth  "DateOfBirth"
                                   :favorite-color "FavoriteColor"
                                   :gender         "Gender"}))

(deftest parse-line-pipe-test
  (is (= expected-record
         (parse-line (:pipe delimitters)
                     "LastName | FirstName | Gender | FavoriteColor | DateOfBirth"))))

(deftest parse-line-comma-test
  (is (= expected-record
         (parse-line (:comma delimitters)
                     "LastName, FirstName, Gender, FavoriteColor, DateOfBirth"))))

(deftest parse-line-space-test
  (is (= expected-record
         (parse-line (:space delimitters)
                     "LastName FirstName Gender FavoriteColor DateOfBirth"))))
