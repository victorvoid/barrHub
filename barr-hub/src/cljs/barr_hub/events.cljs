(ns barr-hub.events
    (:require [re-frame.core :as re-frame]
              [barr-hub.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/app-db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))
