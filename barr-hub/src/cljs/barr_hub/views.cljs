(ns barr-hub.views
  (:require [re-frame.core :as re-frame]
            [devtools.core :as devtools]
            [secretary.core :as secretary]))

;; Input Search
(defn redirect! [loc]
  (set! (.-location js/window) loc))

(defn set-hash! [loc]
  (set! (.-hash js/window.location) loc))
(defrecord User [id]
  secretary/IRenderRoute
  (render-route [_]
    (str "/#/about"))

  (render-route [this params]
    (str (secretary/render-route this) "?"
         (secretary/encode-query-params params))))

(defn input-search []
  (let [value (re-frame/subscribe [:search-key])]
    (fn []
      [:form {:action ""}
      [:input {:name "search"
                :placeholder "Search GitHub"
                :type "search"
                :required ""
                :value @value
                :style {:width "80%"
                        :height "38px"
                        :padding-left "5px"
                        :border "1px solid #ddd"
                        :outline "none"
                        :transition "all .2s"
                        :margin-right "5px"}
                :on-change #(re-frame/dispatch [:search-key (-> % .-target .-value)])}]
       [:button {:class "btn btn__state--default"
                 :type "submit"
                 :on-click #(do
                              (.preventDefault %)
                              (set-hash! (str "/search/?q=" @value)))} "Submit"]])))

;; home
(defn home-panel []
    (fn []
      [:section {:style {:padding "100px"
                         :max-width "600px"
                         :margin "0 auto"
                         :text-align "center"}}
       [:img {:title "Github's Octocat mascot"
              :style {:max-width "300px"}
              :alt "Mascot of the GitHub"
              :src "http://cameronmcefee.com/img/work/the-octocat/codercat.jpg"}]
       [:h2 {:style { :font-weight "300"}} "Search more than "
        [:strong "19M"] " users"]
       [input-search]]))


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
     [input-search]
     [search-repositories-aside]
     [search-repositories-container]]))


;; about

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "#/"} "go to Home Page"]]]))


;; main

(defn- panels [panel-name]
  (.log js/console (str "pane-name " panel-name))
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :search-repositories-panel [search-repositories-list]
    [:div "not found"]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
