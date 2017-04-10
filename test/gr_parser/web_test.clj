(ns gr-parser.web-test
  (:require [gr-parser.web :as sut]
            [clojure.test :refer :all]
            [cheshire.core :as json]
            [ring.mock.request :as mock]))

(deftest create-record-test
  (testing "People can be POST'd to the database with different delimitters"
    (let [people-db (atom [])
          handler (sut/make-app people-db)
          response (handler
                    (mock/request :post "/records" {:delimitter "pipe"
                                                    :data "B | C | Male | Blue | 5/18/1993"}))]
      (is (= 200 (:status response)))
      (is (= 1 (count @people-db)))
      (let [response (handler
                      (mock/request :post "/records" {:delimitter "comma"
                                                      :data "H, M, Female, Red, 3/3/1987"}))]
        (is (= 200 (:status response))))
      (is (= 2 (count @people-db)))
      (let [response (handler
                      (mock/request :post "/records" {:delimitter "space"
                                                      :data "C E Male Blue 2/10/1987"}))]
        (is (= 200 (:status response))))
      (is (= 3 (count @people-db)))
      (is (= [#gr_parser.core.Person{:last-name      "B",
                                     :first-name     "C",
                                     :gender         "Male",
                                     :favorite-color "Blue",
                                     :date-of-birth  #inst "1993-05-18T05:00:00.000-00:00"}
              #gr_parser.core.Person{:last-name      "H",
                                     :first-name     "M",
                                     :gender         "Female",
                                     :favorite-color "Red",
                                     :date-of-birth  #inst "1987-03-03T06:00:00.000-00:00"}
              #gr_parser.core.Person{:last-name      "C",
                                     :first-name     "E",
                                     :gender         "Male",
                                     :favorite-color "Blue",
                                     :date-of-birth  #inst "1987-02-10T06:00:00.000-00:00"}]
             @people-db)))))

(def db-state (atom [#gr_parser.core.Person{:last-name      "B",
                                            :first-name     "C",
                                            :gender         "Male",
                                            :favorite-color "Blue",
                                            :date-of-birth  #inst "1993-05-18T05:00:00.000-00:00"}
                     #gr_parser.core.Person{:last-name      "H",
                                            :first-name     "M",
                                            :gender         "Female",
                                            :favorite-color "Red",
                                            :date-of-birth  #inst "1987-03-03T06:00:00.000-00:00"}
                     #gr_parser.core.Person{:last-name      "C",
                                            :first-name     "E",
                                            :gender         "Male",
                                            :favorite-color "Blue",
                                            :date-of-birth  #inst "1987-02-10T06:00:00.000-00:00"}]))

(deftest get-records-gender-test
  (testing "Records are sorted by gender, female first, then by last-name"
    (let [handler   (sut/make-app db-state)
          resp-body (-> (handler (mock/request :get "/records/gender"))
                        (get :body)
                        (json/parse-string true))]
      (is (= [{:last-name      "H"
               :first-name     "M"
               :gender         "Female"
               :favorite-color "Red"
               :date-of-birth  "03/03/1987"}
              {:last-name      "B"
               :first-name     "C"
               :gender         "Male"
               :favorite-color "Blue"
               :date-of-birth  "05/18/1993"}
              {:last-name      "C"
               :first-name     "E"
               :gender         "Male"
               :favorite-color "Blue"
               :date-of-birth  "02/10/1987"}]
             resp-body)))))

(deftest get-records-name-test
  (testing "Records are sorted by last-name descending"
    (let [handler   (sut/make-app db-state)
          resp-body (-> (handler (mock/request :get "/records/name"))
                        (get :body)
                        (json/parse-string true))]
      (is (= [{:last-name      "H"
               :first-name     "M"
               :gender         "Female"
               :favorite-color "Red"
               :date-of-birth  "03/03/1987"}
              {:last-name      "C"
               :first-name     "E"
               :gender         "Male"
               :favorite-color "Blue"
               :date-of-birth  "02/10/1987"}
              {:last-name      "B"
               :first-name     "C"
               :gender         "Male"
               :favorite-color "Blue"
               :date-of-birth  "05/18/1993"}]
             resp-body)))))

(deftest get-records-birthdate-test
  (testing "Records are sorted by birthdate ascending"
    (let [handler   (sut/make-app db-state)
          resp-body (-> (handler (mock/request :get "/records/birthdate"))
                        (get :body)
                        (json/parse-string true))]
      (is (= [{:last-name      "C"
               :first-name     "E"
               :gender         "Male"
               :favorite-color "Blue"
               :date-of-birth  "02/10/1987"}
              {:last-name      "H"
               :first-name     "M"
               :gender         "Female"
               :favorite-color "Red"
               :date-of-birth  "03/03/1987"}
              {:last-name      "B"
               :first-name     "C"
               :gender         "Male"
               :favorite-color "Blue"
               :date-of-birth  "05/18/1993"}]
             resp-body)))))
