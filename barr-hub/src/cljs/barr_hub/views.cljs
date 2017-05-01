(ns barr-hub.views
  (:require [re-frame.core :as re-frame]
            [devtools.core :as devtools]
            [secretary.core :as secretary]
            [barr-hub.components.home :as home]
            [barr-hub.components.about :as about]
            [barr-hub.components.search-repositories :as search]))

;; main

(defn- panels [panel-name]
  (.log js/console (str "pane-name " panel-name))
  (case panel-name
    :home-panel [home/home-panel]
    :about-panel [about/about-panel]
    :search-repositories-panel [search/search-repositories-list]
    [:div "not found"]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
