(ns barr-hub.events
  (:require [re-frame.core :as re-frame]
            [ajax.core :refer [GET]]
            [barr-hub.db :as db]))




(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/app-db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 :search-key
 (fn [db [_ text]]
   (assoc db :search-key text)))


(re-frame/reg-event-db
 :request-success-repositories
 (fn [db [_ response]]
   (assoc db :repositories-search-list response)))

;; search repositories

(re-frame/reg-event-db
 :request-search
 (fn
   [db [_ query-params]]

   (re-frame/dispatch [:search-key (get query-params :q)])

   (GET
    (str "https://api.github.com/search/repositories?q=" (get query-params :q))
    {
     :type :json
     :handler #(re-frame/dispatch [:request-success-repositories %1])
     :error-handler #(js/console.log (str "error :/" %1))})))
