(ns barr-hub.components.search-repositories
  (:require [re-frame.core :as re-frame]
            [barr-hub.components.inputs :as inputs]))

;search repositories

(defn search-repositories-container []
  (let [repositories (re-frame/subscribe [:repositories-search-list])]
    (.log js/console @repositories)
    (fn []
      [:section {:class "repositories__container "}
      [:h2 {:class "found__title "} (str "We’ve found " (get @repositories "total_count") " repository results")]
       [:ul {:class "search__list"}
        (map (fn [repository]
               [:li {:class "search__item" :key (get repository "id")}
                [:h3 {:class "search__item__name"}
                 [:a {:href (get repository "html_url")} (get repository "full_name")]]]) (get @repositories "items"))]])))

(defn search-repositories-aside []
  (let [repositories (re-frame/subscribe [:repositories-search-list])]
  (fn []
    [:aside {:class "search__menu"}
     [:nav
      [:ul {:class "search__menu--block"}
       [:li {:class "search__menu--item"}
        [:a {:title "Will list all repositories of your Search"}
         [:svg {:width "40" :height "40" :class "icon-repositories"}
          [:use {:xlinkHref "#repository"}]] "Repositories"
         [:span (get @repositories "total_count")]]]
       [:li {:class "search__menu--item"}
        [:a {:title "Users will be listed"}
         [:svg {:width "40" :height "40" :class "icon-user"}
          [:use {:xlinkHref "#user"}]] "Users"
         [:span "3000"]]]]]])))

(defn search-repositories-list []
  (fn []
    [:section {:class "search__container"}
     [inputs/input-search]
     [search-repositories-aside]
     [search-repositories-container]]))

