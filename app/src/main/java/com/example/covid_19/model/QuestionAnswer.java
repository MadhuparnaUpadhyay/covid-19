package com.example.covid_19.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswer {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<QuestionAnswerModel> ITEMS = new ArrayList<QuestionAnswerModel>();

    static {
        ITEMS.add(createQuestionAnswer("What is a coronavirus COVID-19?", "COVID-19 is an infectious disease caused by the most recently discovered coronavirus. This new virus and disease were unknown before the outbreak began in Wuhan, China, in December 2019."));
        ITEMS.add(createQuestionAnswer("What are the symptoms of COVID-19?", "The most common symptoms of COVID-19 are :\n" +
                "Fever, tiredness, and dry cough. Some patients may have aches and pains, nasal congestion, runny nose, sore throat or diarrhea. \n" +
                "\n" +
                "Some people become infected but don’t develop any symptoms and don't feel unwell. Most people (about 80%) recover from the disease without needing special treatment. Around 1 out of every 6 people who get COVID-19 becomes seriously ill and develop difficulty breathing. \n" +
                "\n" +
                "Older people, and those with underlying medical problems like high blood pressure, heart problems or diabetes, are more likely to develop serious illness. People with fever, cough and difficulty breathing should seek medical attention.\n"));
        ITEMS.add(createQuestionAnswer("How does COVID-19 spread?", "People can catch COVID-19 from others who have the virus. The disease can spread from person to person through small droplets from the nose or mouth which are spread when a person with COVID-19 coughs or exhales. These droplets land on objects and surfaces around the person. Other people then catch COVID-19 by touching these objects or surfaces, then touching their eyes, nose or mouth. People can also catch COVID-19 if they breathe in droplets from a person with COVID-19 who coughs out or exhales droplets. This is why it is important to stay more than 1 meter (3 feet) away from a person who is sick.\n" +
                "WHO is assessing ongoing research on the ways COVID-19 is spread and will continue to share updated findings.  \n"));
        ITEMS.add(createQuestionAnswer("Protection measures for everyone", "Stay aware of the latest information on the COVID-19 outbreak, available on the WHO website and through your national and local public health authority\n" +
                "You can reduce your chances of being infected or spreading COVID-19 by taking some simple precautions:\n" +
                "Regularly and thoroughly clean your hands with an alcohol-based hand rub or wash them with soap and water.\n" +
                "\n" +
                "\n" +
                "Maintain at least 1 meter (3 feet) distance between yourself and anyone who is coughing or sneezing.\n" +
                "Avoid touching eyes, nose and mouth.\n" +
                "Make sure you, and the people around you, follow good respiratory hygiene. This means covering your mouth and nose with your bent elbow or tissue when you cough or sneeze. Then dispose of the used tissue immediately.\n" +
                "Stay home if you feel unwell. If you have a fever, cough and difficulty breathing, seek medical attention and call in advance. Follow the directions of your local health authority.\n" +
                "Keep up to date on the latest COVID-19 hotspots (cities or local areas where COVID-19 is spreading widely). If possible, avoid traveling to places  – especially if you are an older person or have diabetes, heart or lung disease.\n"));
        ITEMS.add(createQuestionAnswer("How long is the incubation period for COVID-19?", "The “incubation period” means the time between catching the virus and beginning to have symptoms of the disease. Most estimates of the incubation period for COVID-19 range from 1-14 days, most commonly around five days. These estimates will be updated as more data become available."));
        ITEMS.add(createQuestionAnswer("Are antibiotics effective in preventing or treating the COVID-19?", "No. Antibiotics do not work against viruses, they only work on bacterial infections. COVID-19 is caused by a virus, so antibiotics do not work. Antibiotics should not be used as a means of prevention or treatment of COVID-19. They should only be used as directed by a physician to treat a bacterial infection. "));
        ITEMS.add(createQuestionAnswer("What are the travel advises?", "https://www.who.int/ith/en/"));
        ITEMS.add(createQuestionAnswer("How long does the virus survive on surfaces?", "It is not certain how long the virus that causes COVID-19 survives on surfaces, but it seems to behave like other coronaviruses. Studies suggest that coronaviruses (including preliminary information on the COVID-19 virus) may persist on surfaces for a few hours or up to several days. This may vary under different conditions (e.g. type of surface, temperature or humidity of the environment).\n" +
                "If you think a surface may be infected, clean it with simple disinfectant to kill the virus and protect yourself and others. Clean your hands with an alcohol-based hand rub or wash them with soap and water. Avoid touching your eyes, mouth, or nose.\n"));
        ITEMS.add(createQuestionAnswer("COVID Instructions for Pregnancy?", "https://www.who.int/news-room/q-a-detail/q-a-on-covid-19-pregnancy-childbirth-and-breastfeeding"));
        ITEMS.add(createQuestionAnswer("Food and Agriculture Instructions for COVID?", "http://www.fao.org/2019-ncov/q-and-a/en/"));
    }

    private static QuestionAnswerModel createQuestionAnswer(String question, String answer) {
        return new QuestionAnswerModel("Question:-  " + question, "Answer:-  " + answer);
    }
    /**
     * A dummy item representing a piece of content.
     */
    public static class QuestionAnswerModel {
        public final String question;
        public final String answer;

        public QuestionAnswerModel(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        @Override
        public String toString() {
            return question;
        }
    }
}
