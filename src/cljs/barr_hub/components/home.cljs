(ns barr-hub.components.home
  (:require [barr-hub.components.inputs :as inputs]))

(defn home-panel []
  (fn []
    [:section {:class "home__panel"}
     [:img {:title "Github's Octocat mascot"
            :alt "Mascot of the GitHub"
            :src "http://cameronmcefee.com/img/work/the-octocat/codercat.jpg"}]
     [:h2 "Search more than "
      [:strong "19M"] " users"]
     [inputs/input-search]]))
