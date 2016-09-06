package cn.ytxu.api_semi_auto_creater.parser.base;

import org.jsoup.nodes.Element;

import java.util.List;


public class DocEntity extends BaseEntity {
    private List<String> versions;// API所有的版本号
    private List<SectionEntity> sections;

    public DocEntity(BaseEntity higherLevel, Element element) {
        super(higherLevel, element);
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public List<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(List<SectionEntity> sections) {
        this.sections = sections;
    }


    public static class SectionEntity extends BaseEntity<DocEntity> {
        private String name;
        private List<RequestEntity> requests;

        public SectionEntity(DocEntity higherLevel, Element element, String name) {
            super(higherLevel, element);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public List<RequestEntity> getRequests() {
            return requests;
        }

        public void setRequests(List<RequestEntity> requests) {
            this.requests = requests;
        }

        public static SectionEntity copy(SectionEntity section) {
            DocEntity.SectionEntity newSection = new DocEntity.SectionEntity(section.getHigherLevel(), section.getElement(), section.getName());
            return newSection;
        }
    }

    public static class RequestEntity extends BaseEntity<SectionEntity> {
        private String name;
        private String version;

        public RequestEntity(SectionEntity higherLevel, Element element) {
            super(higherLevel, element);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

}