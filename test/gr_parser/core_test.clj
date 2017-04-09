(ns gr-parser.core-test
  (:require [clojure.test :refer :all]
            [gr-parser.core :as sut]))

(def expected-record
  (sut/map->Person {:first-name     "Chris"
                    :last-name      "Bui"
                    :date-of-birth  #inst "1993-05-18T05:00:00.000-00:00"
                    :favorite-color "Blue"
                    :gender         "Male"}))

(deftest parse-line-pipe-test
  (is (= expected-record
         (sut/parse-line (:pipe sut/delimitters)
                         "Bui | Chris | Male | Blue | 5/18/1993"))))

(deftest parse-line-comma-test
  (is (= expected-record
         (sut/parse-line (:comma sut/delimitters)
                         "Bui, Chris, Male, Blue, 5/18/1993"))))

(deftest parse-line-space-test
  (is (= expected-record
         (sut/parse-line (:space sut/delimitters)
                         "Bui Chris Male Blue 5/18/1993"))))

(def test-people [{:first-name "C",
                   :last-name "B",
                   :gender "Male",
                   :favorite-color "Blue",
                   :date-of-birth #inst "1993-05-18T05:00:00.000-00:00"}
                  {:first-name "R",
                   :last-name "S",
                   :gender "Female",
                   :favorite-color "Green",
                   :date-of-birth #inst "1993-09-10T05:00:00.000-00:00"}
                  {:first-name "B",
                   :last-name "R",
                   :gender "Male",
                   :favorite-color "Orange",
                   :date-of-birth #inst "1982-02-15T06:00:00.000-00:00"}
                  {:first-name "S",
                   :last-name "G",
                   :gender "Female",
                   :favorite-color "Red",
                   :date-of-birth #inst "1994-01-10T06:00:00.000-00:00"}
                  {:first-name "E",
                   :last-name "C",
                   :gender "Male",
                   :favorite-color "Blue",
                   :date-of-birth #inst "1987-03-13T06:00:00.000-00:00"}])

(deftest sort-gender-last-name-test
  (is (= [{:first-name "S",
           :last-name "G",
           :gender "Female",
           :favorite-color "Red",
           :date-of-birth #inst "1994-01-10T06:00:00.000-00:00"}
          {:first-name "R",
           :last-name "S",
           :gender "Female",
           :favorite-color "Green",
           :date-of-birth #inst "1993-09-10T05:00:00.000-00:00"}
          {:first-name "C",
           :last-name "B",
           :gender "Male",
           :favorite-color "Blue",
           :date-of-birth #inst "1993-05-18T05:00:00.000-00:00"}
          {:first-name "E",
           :last-name "C",
           :gender "Male",
           :favorite-color "Blue",
           :date-of-birth #inst "1987-03-13T06:00:00.000-00:00"}
          {:first-name "B",
           :last-name "R",
           :gender "Male",
           :favorite-color "Orange",
           :date-of-birth #inst "1982-02-15T06:00:00.000-00:00"}]
         (sut/sort [:gender :last-name] test-people))
      "Females come first and each gender is sorted by last-name"))

(deftest sort-gender-last-name-test
  (is (= [{:first-name "B",
           :last-name "R",
           :gender "Male",
           :favorite-color "Orange",
           :date-of-birth #inst "1982-02-15T06:00:00.000-00:00"}
          {:first-name "E",
           :last-name "C",
           :gender "Male",
           :favorite-color "Blue",
           :date-of-birth #inst "1987-03-13T06:00:00.000-00:00"}
          {:first-name "C",
           :last-name "B",
           :gender "Male",
           :favorite-color "Blue",
           :date-of-birth #inst "1993-05-18T05:00:00.000-00:00"}
          {:first-name "R",
           :last-name "S",
           :gender "Female",
           :favorite-color "Green",
           :date-of-birth #inst "1993-09-10T05:00:00.000-00:00"}
          {:first-name "S",
           :last-name "G",
           :gender "Female",
           :favorite-color "Red",
           :date-of-birth #inst "1994-01-10T06:00:00.000-00:00"}]
         (sut/sort :date-of-birth test-people))
      "Sorted by date of birth"))

(deftest sort-gender-last-name-test
  (is (= [{:first-name "R",
           :last-name "S",
           :gender "Female",
           :favorite-color "Green",
           :date-of-birth #inst "1993-09-10T05:00:00.000-00:00"}
          {:first-name "B",
           :last-name "R",
           :gender "Male",
           :favorite-color "Orange",
           :date-of-birth #inst "1982-02-15T06:00:00.000-00:00"}
          {:first-name "S",
           :last-name "G",
           :gender "Female",
           :favorite-color "Red",
           :date-of-birth #inst "1994-01-10T06:00:00.000-00:00"}
          {:first-name "E",
           :last-name "C",
           :gender "Male",
           :favorite-color "Blue",
           :date-of-birth #inst "1987-03-13T06:00:00.000-00:00"}
          {:first-name "C",
           :last-name "B",
           :gender "Male",
           :favorite-color "Blue",
           :date-of-birth #inst "1993-05-18T05:00:00.000-00:00"}]
         (sut/sort :last-name test-people))
      "Sorted by last-name descending"))
