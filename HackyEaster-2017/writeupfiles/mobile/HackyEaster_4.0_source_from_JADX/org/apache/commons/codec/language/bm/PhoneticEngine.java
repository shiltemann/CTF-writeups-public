package org.apache.commons.codec.language.bm;

import android.support.v4.app.NotificationCompat.WearableExtender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.codec.language.bm.Languages.LanguageSet;
import org.apache.commons.codec.language.bm.Rule.Phoneme;
import org.apache.commons.codec.language.bm.Rule.PhonemeExpr;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.message.TokenParser;
import ps.hacking.hackyeaster.android.BuildConfig;

public class PhoneticEngine {
    private static final int DEFAULT_MAX_PHONEMES = 20;
    private static final Map<NameType, Set<String>> NAME_PREFIXES;
    private final boolean concat;
    private final Lang lang;
    private final int maxPhonemes;
    private final NameType nameType;
    private final RuleType ruleType;

    /* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine.1 */
    static /* synthetic */ class C00671 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$codec$language$bm$NameType;

        static {
            $SwitchMap$org$apache$commons$codec$language$bm$NameType = new int[NameType.values().length];
            try {
                $SwitchMap$org$apache$commons$codec$language$bm$NameType[NameType.SEPHARDIC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$codec$language$bm$NameType[NameType.ASHKENAZI.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$apache$commons$codec$language$bm$NameType[NameType.GENERIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static final class PhonemeBuilder {
        private final Set<Phoneme> phonemes;

        public static PhonemeBuilder empty(LanguageSet languages) {
            return new PhonemeBuilder(new Phoneme(BuildConfig.FLAVOR, languages));
        }

        private PhonemeBuilder(Phoneme phoneme) {
            this.phonemes = new LinkedHashSet();
            this.phonemes.add(phoneme);
        }

        private PhonemeBuilder(Set<Phoneme> phonemes) {
            this.phonemes = phonemes;
        }

        public void append(CharSequence str) {
            for (Phoneme ph : this.phonemes) {
                ph.append(str);
            }
        }

        public void apply(PhonemeExpr phonemeExpr, int maxPhonemes) {
            Set<Phoneme> newPhonemes = new LinkedHashSet(maxPhonemes);
            loop0:
            for (Phoneme left : this.phonemes) {
                for (Phoneme right : phonemeExpr.getPhonemes()) {
                    LanguageSet languages = left.getLanguages().restrictTo(right.getLanguages());
                    if (!languages.isEmpty()) {
                        Phoneme join = new Phoneme(left, right, languages);
                        if (newPhonemes.size() < maxPhonemes) {
                            newPhonemes.add(join);
                            if (newPhonemes.size() >= maxPhonemes) {
                                break loop0;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            this.phonemes.clear();
            this.phonemes.addAll(newPhonemes);
        }

        public Set<Phoneme> getPhonemes() {
            return this.phonemes;
        }

        public String makeString() {
            StringBuilder sb = new StringBuilder();
            for (Phoneme ph : this.phonemes) {
                if (sb.length() > 0) {
                    sb.append("|");
                }
                sb.append(ph.getPhonemeText());
            }
            return sb.toString();
        }
    }

    private static final class RulesApplication {
        private final Map<String, List<Rule>> finalRules;
        private boolean found;
        private int f10i;
        private final CharSequence input;
        private final int maxPhonemes;
        private PhonemeBuilder phonemeBuilder;

        public RulesApplication(Map<String, List<Rule>> finalRules, CharSequence input, PhonemeBuilder phonemeBuilder, int i, int maxPhonemes) {
            if (finalRules == null) {
                throw new NullPointerException("The finalRules argument must not be null");
            }
            this.finalRules = finalRules;
            this.phonemeBuilder = phonemeBuilder;
            this.input = input;
            this.f10i = i;
            this.maxPhonemes = maxPhonemes;
        }

        public int getI() {
            return this.f10i;
        }

        public PhonemeBuilder getPhonemeBuilder() {
            return this.phonemeBuilder;
        }

        public RulesApplication invoke() {
            this.found = false;
            int patternLength = 1;
            List<Rule> rules = (List) this.finalRules.get(this.input.subSequence(this.f10i, this.f10i + 1));
            if (rules != null) {
                for (Rule rule : rules) {
                    patternLength = rule.getPattern().length();
                    if (rule.patternAndContextMatches(this.input, this.f10i)) {
                        this.phonemeBuilder.apply(rule.getPhoneme(), this.maxPhonemes);
                        this.found = true;
                        break;
                    }
                }
            }
            if (!this.found) {
                patternLength = 1;
            }
            this.f10i += patternLength;
            return this;
        }

        public boolean isFound() {
            return this.found;
        }
    }

    static {
        NAME_PREFIXES = new EnumMap(NameType.class);
        NAME_PREFIXES.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"bar", "ben", "da", "de", "van", "von"}))));
        NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"}))));
        NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"}))));
    }

    private static String join(Iterable<String> strings, String sep) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> si = strings.iterator();
        if (si.hasNext()) {
            sb.append((String) si.next());
        }
        while (si.hasNext()) {
            sb.append(sep).append((String) si.next());
        }
        return sb.toString();
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean concat) {
        this(nameType, ruleType, concat, DEFAULT_MAX_PHONEMES);
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean concat, int maxPhonemes) {
        if (ruleType == RuleType.RULES) {
            throw new IllegalArgumentException("ruleType must not be " + RuleType.RULES);
        }
        this.nameType = nameType;
        this.ruleType = ruleType;
        this.concat = concat;
        this.lang = Lang.instance(nameType);
        this.maxPhonemes = maxPhonemes;
    }

    private PhonemeBuilder applyFinalRules(PhonemeBuilder phonemeBuilder, Map<String, List<Rule>> finalRules) {
        if (finalRules == null) {
            throw new NullPointerException("finalRules can not be null");
        } else if (finalRules.isEmpty()) {
            return phonemeBuilder;
        } else {
            Set<Phoneme> phonemes = new TreeSet(Phoneme.COMPARATOR);
            for (Phoneme phoneme : phonemeBuilder.getPhonemes()) {
                PhonemeBuilder subBuilder = PhonemeBuilder.empty(phoneme.getLanguages());
                String phonemeText = phoneme.getPhonemeText().toString();
                int i = 0;
                while (i < phonemeText.length()) {
                    RulesApplication rulesApplication = new RulesApplication(finalRules, phonemeText, subBuilder, i, this.maxPhonemes).invoke();
                    boolean found = rulesApplication.isFound();
                    subBuilder = rulesApplication.getPhonemeBuilder();
                    if (!found) {
                        subBuilder.append(phonemeText.subSequence(i, i + 1));
                    }
                    i = rulesApplication.getI();
                }
                phonemes.addAll(subBuilder.getPhonemes());
            }
            return new PhonemeBuilder(null);
        }
    }

    public String encode(String input) {
        return encode(input, this.lang.guessLanguages(input));
    }

    public String encode(String input, LanguageSet languageSet) {
        Map<String, List<Rule>> rules = Rule.getInstanceMap(this.nameType, RuleType.RULES, languageSet);
        Map<String, List<Rule>> finalRules1 = Rule.getInstanceMap(this.nameType, this.ruleType, "common");
        Map<String, List<Rule>> finalRules2 = Rule.getInstanceMap(this.nameType, this.ruleType, languageSet);
        input = input.toLowerCase(Locale.ENGLISH).replace('-', TokenParser.SP).trim();
        if (this.nameType == NameType.GENERIC) {
            String remainder;
            if (input.length() < 2 || !input.substring(0, 2).equals("d'")) {
                for (String l : (Set) NAME_PREFIXES.get(this.nameType)) {
                    if (input.startsWith(l + " ")) {
                        remainder = input.substring(l.length() + 1);
                        return "(" + encode(remainder) + ")-(" + encode(l + remainder) + ")";
                    }
                }
            }
            remainder = input.substring(2);
            return "(" + encode(remainder) + ")-(" + encode("d" + remainder) + ")";
        }
        List<String> words = Arrays.asList(input.split("\\s+"));
        List<String> words2 = new ArrayList();
        switch (C00671.$SwitchMap$org$apache$commons$codec$language$bm$NameType[this.nameType.ordinal()]) {
            case WearableExtender.SIZE_XSMALL /*1*/:
                for (String aWord : words) {
                    String[] parts = aWord.split("'");
                    words2.add(parts[parts.length - 1]);
                }
                words2.removeAll((Collection) NAME_PREFIXES.get(this.nameType));
                break;
            case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                words2.addAll(words);
                words2.removeAll((Collection) NAME_PREFIXES.get(this.nameType));
                break;
            case WearableExtender.SIZE_MEDIUM /*3*/:
                words2.addAll(words);
                break;
            default:
                throw new IllegalStateException("Unreachable case: " + this.nameType);
        }
        if (this.concat) {
            input = join(words2, " ");
        } else if (words2.size() == 1) {
            input = (String) words.iterator().next();
        } else {
            StringBuilder result = new StringBuilder();
            for (String word : words2) {
                StringBuilder stringBuilder = result;
                stringBuilder.append("-").append(encode(word));
            }
            return result.substring(1);
        }
        PhonemeBuilder phonemeBuilder = PhonemeBuilder.empty(languageSet);
        int i = 0;
        while (i < input.length()) {
            RulesApplication rulesApplication = new RulesApplication(rules, input, phonemeBuilder, i, this.maxPhonemes).invoke();
            i = rulesApplication.getI();
            phonemeBuilder = rulesApplication.getPhonemeBuilder();
        }
        return applyFinalRules(applyFinalRules(phonemeBuilder, finalRules1), finalRules2).makeString();
    }

    public Lang getLang() {
        return this.lang;
    }

    public NameType getNameType() {
        return this.nameType;
    }

    public RuleType getRuleType() {
        return this.ruleType;
    }

    public boolean isConcat() {
        return this.concat;
    }

    public int getMaxPhonemes() {
        return this.maxPhonemes;
    }
}
